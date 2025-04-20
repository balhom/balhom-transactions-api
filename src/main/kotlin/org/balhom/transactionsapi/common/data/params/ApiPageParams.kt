package org.balhom.transactionsapi.common.data.params

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.ws.rs.DefaultValue
import org.balhom.transactionsapi.common.data.props.ApiPageProps
import org.jboss.resteasy.reactive.RestQuery

class ApiPageParams(
    @field:RestQuery
    @field:DefaultValue("10")
    @field:Min(1)
    @field:Max(20)
    var pageSize: Int,

    @field:RestQuery
    @field:DefaultValue("0")
    @field:Min(0)
    var pageNum: Int,
) {
    constructor() : this(10, 0)

    fun toProps(): ApiPageProps = ApiPageProps(
        pageSize,
        pageNum,
    )
}

