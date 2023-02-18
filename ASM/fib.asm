.data
	start_msg: .asciiz "How many Fibonacci numbers would you like? "
	end_msg: .asciiz "Thanks for generating Fibonacci numbers!\n"
	newline: .asciiz "\n"
	numOfNumbers: .word 0

.text
	.globl main
main:
	li $v0, 4 # greet user
	la $a0, start_msg
	syscall
	
	li $v0, 5 # read an integer from the command line
	syscall
	
	sw $v0, numOfNumbers # save value
	lw $s0, numOfNumbers # copy to register
	li $s1, 1 # current fib 1
	li $s2, 1 # current fib 2
	li $s3, 0 # counter
	jal fib
	
fib:
	bge $s3, $s0, exit # exit condition
	
	li $v0, 1
	move $a0, $s1
	syscall
	
	li $v0, 4
	la $a0, newline
	syscall
	
	addu $t0, $s1, $s2
	move $s1, $s2
	move $s2, $t0
	addiu $s3 $s3, 1
	j fib
	
exit:
	li $v0, 4
	la $a0, end_msg
	syscall
	