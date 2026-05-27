# 后端包结构设计

目标结构：common、interfaces、application、domain、infrastructure。

interfaces 下放 controller、request、response、assembler。application 下放 command、query、dto、service。domain 下放 user、problem、submission、judge、sandbox、classroom、statistics、shared。infrastructure 下放 persistence/entity、persistence/mapper、persistence/converter、repository、sandbox、redis、security、config。common 下放 result、exception、enums、util、constants。

基础类：ApiResponse、PageResponse、BusinessException、ErrorCode、GlobalExceptionHandler、BaseEntity。
