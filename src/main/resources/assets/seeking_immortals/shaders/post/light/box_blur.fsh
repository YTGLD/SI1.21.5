#version 150

uniform sampler2D InSampler;
in vec2 texCoord;
in vec2 sampleStep;

uniform float Radius;


uniform vec2 focusPoint;  // 添加：指定需要模糊的位置
uniform float focusRadius;  // 添加：指定模糊处理的半径范围

out vec4 fragColor;

void main() {
    vec4 color = texture(InSampler, texCoord);  // 获取原始颜色

    // 计算当前片段与focusPoint之间的距离
    float distance = length(texCoord - focusPoint);

    if (distance <= focusRadius) {  // 如果距离在模糊处理的半径范围内
        vec4 blurred = vec4(0.0);
        float actualRadius = round(Radius);
        for (float a = -actualRadius + 0.5; a <= actualRadius; a += 2.0) {
            blurred += texture(InSampler, texCoord + sampleStep * a);
        }
        blurred += texture(InSampler, texCoord + sampleStep * actualRadius) / 2.0;
        fragColor = blurred / (actualRadius + 0.5);
    } else {  // 否则
        fragColor = color;  // 直接输出原始颜色
    }
}
