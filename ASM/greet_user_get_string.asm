.data
	test_str: .asciiz ""
	greet_str: .asciiz "Please type in 80 characters or less\n"

.text
	.globl main

main:
	li $v0, 4 # greet user
	la $a0, greet_str
	syscall
	
	li $v0, 8
	la $a0, test_str
	la $a1, 80
	syscall
	
	li $v0, 4
	la $a0, test_str
	syscall
	