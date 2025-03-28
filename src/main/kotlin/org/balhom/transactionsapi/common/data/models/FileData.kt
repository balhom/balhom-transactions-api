package org.balhom.transactionsapi.common.data.models

import java.io.File

data class FileData(
    var data: File,
    var filePath: String,
    var mimetype: String,
)
