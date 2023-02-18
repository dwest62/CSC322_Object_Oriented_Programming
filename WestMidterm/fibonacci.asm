.data
	start_msg:.asciiz "How many Fibonacci numbers would you like? "
	end_msg:.asciiz "Thanks for generating Fibonacci numbers!\n"
 	newline:.asciiz "\n"
 	numOfNumbers:.word 0
.text
	.globl main

main: 
	li $v0, 4 # greet the user
 	la $a0, start_msg
 	syscall
 	
 	li $v0, 5 # read an integer from the command line
 	syscall
 	
 	sw $v0, numOfNumbers # we’ll save the value the user typed in
 	lw $s0, numOfNumbers # copy this to our register
 	li $s1, 1 # the first Fibonacci number
 	
 	li $s2, 1 # the second Fibonacci number
 	li $s3, 0 # a counter to keep track of how many numbers we've printed
 	jal fib
 	
fib: 	bge $s3, $s0, exit # if $s3 is greater than or equal to how  many numbers the user wants, we're done!
  	li $v0, 1 # print out the current Fibonacci number
 	move $a0, $s1
 	syscall
 	li $v0, 4 # print a newline
 	la $a0, newline
 	syscall
 	addu $t0, $s1, $s2 # calculate the next Fibonacci number
 	move $s1, $s2 # move the second number into the first
 	move $s2, $t0 # move the new new Fibonacci number into second register
 	addiu $s3, $s3, 1 # increment to indicate we've printed a Fibonacci number
 	j fib # recursively loop back to the start of fib.
 exit: 	li $v0, 4
 	la $a0, end_msg
 	syscall
