package com.zebpay.ui.v3.components.utils

class ZTextState(var text:String, private val regex:String?=null){

    var isError:Boolean = false
    var error: String = ""

    fun setText(text: String):ZTextState{
        this.text=text
        isError=false
        return this
    }

    fun setError(msg:String):ZTextState{
        isError = true
        this.error = msg
        return this
    }

    fun isValid():Boolean{
        if(text.isEmpty()) {
            return false
        }
        if(!regex.isNullOrEmpty() && !Regex(regex).matches(text)){
            return false
        }
        return true
    }

    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        var result = isError.hashCode()
        result = 31 * result + error.hashCode()
        return result
    }
}
