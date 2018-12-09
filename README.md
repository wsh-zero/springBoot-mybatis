swagger通过注解表明该接口会生成文档，包括接口名、请求方法、参数、返回信息的等等。

    @Api：修饰整个类，描述Controller的作用
    @ApiOperation：描述一个类的一个方法，或者说一个接口
    @ApiParam：单个参数描述
    @ApiModel：用对象来接收参数
    @ApiProperty：用对象接收参数时，描述对象的一个字段
    @ApiResponse：HTTP响应其中1个描述
    @ApiResponses：HTTP响应整体描述
    @ApiIgnore：使用该注解忽略这个API
    @ApiError ：发生错误返回的信息
    @ApiImplicitParam：一个请求参数
    @ApiImplicitParams：多个请求参数

spring 注解验证@NotNull等使用方法

    @Null  被注释的元素必须为null
    @NotNull  被注释的元素不能为null
    @AssertTrue  被注释的元素必须为true
    @AssertFalse  被注释的元素必须为false
    @Min(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值
    @Max(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值
    @DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值
    @DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值
    @Size(max,min)  被注释的元素的大小必须在指定的范围内。
    @Digits(integer,fraction)  被注释的元素必须是一个数字，其值必须在可接受的范围内
    @Past  被注释的元素必须是一个过去的日期
    @Future  被注释的元素必须是一个将来的日期
    @Pattern(value) 被注释的元素必须符合指定的正则表达式。
    @Email 被注释的元素必须是电子邮件地址
    @Length 被注释的字符串的大小必须在指定的范围内
    @NotEmpty  被注释的字符串必须非空
    @Range  被注释的元素必须在合适的范围内
layuiAdmin学习资料
    https://faysunshine.com/layui/template/index.html （select多选，tree列表优化实现右键删除等）