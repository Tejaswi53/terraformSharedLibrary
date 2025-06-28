package org.dlv.terraform

class terraformUtils {
    def stack

    terraformUtils(stack) {
        this.stack = stack
    }

    def formatCheck(string Customer){
        stack.sh """
          cd ${stack.env.WORKSPACE}/stacks/${Customer}
          sudo terraform fmt -check
        """
    }
}