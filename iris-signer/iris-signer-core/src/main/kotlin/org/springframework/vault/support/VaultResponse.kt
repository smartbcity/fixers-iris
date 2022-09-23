package org.springframework.vault.support

import com.fasterxml.jackson.annotation.JsonIgnore

class VaultResponse: VaultResponseSupport<MutableMap<String, Any>>() {

    @JsonIgnore
    override fun getRequiredAuth(): MutableMap<String, Any> {
        return super.getRequiredAuth()
    }
}
