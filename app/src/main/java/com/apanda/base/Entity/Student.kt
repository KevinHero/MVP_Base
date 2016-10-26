package com.apanda.base.Entity

/**
 * Created by Android on 2016/10/17.
 */

class Student {
    var name: String? = null
    var addr: String? = null

    constructor() {
    }

    constructor(name: String, addr: String) {
        this.name = name
        this.addr = addr
    }
}