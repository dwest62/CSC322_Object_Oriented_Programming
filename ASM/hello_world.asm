.data
	welcome_str: .asciiz "Hello world!\n"
.text
	.globl main
	
main:
	li $v0, 4
	la $a0, welcome_str
	syscall