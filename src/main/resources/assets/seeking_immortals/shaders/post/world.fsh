#version 120

#define CONST_EXP 2048
#define CONST_EXP2 2049
#define CONST_LINEAR 9729

uniform sampler2D InSampler;

uniform vec3 positionLight;
uniform vec3 color_light;
uniform float radius_light;
uniform int u_lightSourcesAmount;

//World time in ticks

//Fragment position [0.0, 1.0][0.0, 1.0]
in vec2 texCoord;

out vec4 fragColor;

//Calculates the fragment world position (relative to camera)
vec3 getFragPos(sampler2D depthMap) {
    //Using the texture coordinate and the depth, the original vertex in world space coordinates can be calculated
    //The depth value from the depth buffer is not linear
    float zBuffer = texture2D(depthMap, texCoord).x;
    //float fragDepth = pow(zBuffer, 2);
	float fragDepth = zBuffer * 2.0F - 1.0F;
    
    //Calculate fragment world position relative to the camera position
    vec4 fragRelPos = vec4(texCoord.xy * 2.0F - 1.0F, fragDepth, 1.0F) ;
    fragRelPos.xyz /= fragRelPos.w;
    
    return fragRelPos.xyz;
}

//Returns the fog color multiplier for a fragment
float getFogMultiplier(vec3 fragPos) {
    return 1.0F;
}

//Applies fog to the color of a fragment


void main() {
    //Get fragment world position
    vec3 fragPos = getFragPos(InSampler);
    
    //A color multiplier that is applied to the final color
    float colorMultiplier = 1.0F;
    
    //Strength of distortion
    float distortionMultiplier = 0.0F;
    
    //Holds the calculated color
    vec4 color = vec4(0.0F, 0.0F, 0.0F, 0.0F);
    
    
    
    
    //////// Lighting (Distortion) ////////
    //Calculate distance from fragment to light sources and apply color
    for(int i = 0; i < u_lightSourcesAmount; i++) {
        vec3 lightPos = positionLight;
        float dist = distance(lightPos, fragPos);
        float radius = radius_light;
        if(dist < radius) {
            vec3 lightColor = color_light;
            if(lightColor.r == -1 && lightColor.g == -1 && lightColor.b == -1) {
				if(distortionMultiplier < 0.6F) {
					distortionMultiplier += max(distortionMultiplier, 1.0F - pow(dist / radius, 4));
				}
            }
        }
    }
    
    
    


    //////// Lighting ////////
    //Calculate distance from fragment to light sources and apply color
	float lightingFogMultiplier = 1.0F - getFogMultiplier(fragPos);
    for(int i = 0; i < u_lightSourcesAmount; i++) {
        vec3 lightPos = positionLight;
        float dist = distance(lightPos, fragPos);
        float radius = radius_light;
        vec3 lightColor = color_light;
        
        if(dist < radius) {
            if(lightColor.r != -1 || lightColor.g != -1 || lightColor.b != -1) {
                color += (vec4(lightColor * pow(1.0F - dist / radius, 2), 0.0F) * lightingFogMultiplier);
            }
        }
    }

    fragColor = color * colorMultiplier;
}


