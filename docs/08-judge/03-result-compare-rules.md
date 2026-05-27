# 结果集比较规则

比较列数、列名、行数、字段值、行顺序、NULL、BigDecimal、时间类型。

如果 checkColumnName=true，列名必须一致。如果 orderSensitive=true，逐行比较；否则按所有列转字符串后排序再比较。NULL 和空字符串严格区分。BigDecimal 使用数值比较，1.0 等于 1.00。

diff 信息必须明确，例如：第 2 行第 3 列不一致，expected=100，actual=90。
