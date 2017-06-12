// Groovy version of https://codon.com/programming-with-nothing 

zero = {p -> { x -> x }}
one = {p -> { x -> p(x) }}
two = {p -> { x -> p(p(x)) }}
three = {p -> { x -> p(p(p(x))) }}
five = {p -> { x -> p(p(p(p(p(x))))) }}
fifteen = {p -> {x -> p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(x))))))))))))))) }}

hundred = { p -> {x -> 
p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(p(x)))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))) 
}}

increment = { n -> { p -> { x -> p(n(p)(x)) } } }
decrement = { n -> { f -> { x -> n({g -> {h -> h(g(f)) }})({y -> x })({ y-> y }) } } }
add = {m ->  {n ->  n(increment)(m) } }
subtract = {m -> {n ->  n(decrement)(m) } }
mult = {m -> {n -> n(add(m))(zero) } }
pow = {m -> {n -> n(mult(m))(one) } }
to_integer = {p -> p({ n->n + 1 })(0)}

TRUE = {x -> { y -> x}}
FALSE = {x -> { y -> y}}
IF = {proc -> { x ->  { y -> proc(x)(y) }}}
to_boolean = {proc -> IF(proc)(true)(false) }

isZero = { n -> n({x->FALSE})(TRUE) }
isLessOrEqual = { m -> { n -> isZero(subtract(m)(n))}}

z = {f -> {x -> f({ y -> x(x)(y) }) }({x -> f({y -> x(x)(y)})})} // z combinator, a version of y combinator for non-strict languages
def mod
mod = z( {f -> { m-> { n -> IF(isLessOrEqual(n)(m))({x -> f(subtract(m)(n))(n)(x)})(m)} } })

pair = {x -> { y ->  { f ->  f(x)(y) } } }
left  = {p -> p( { x -> { y -> x } } ) }
right = {p -> p( {x -> {y -> y } } ) }

empty     = pair(TRUE)(TRUE)
unshift   = {l -> { x -> pair(FALSE)(pair(x)(l))} }
is_empty  = left
first     = { l -> left(right(l)) }
rest      = { l -> right(right(l)) }

def to_array(proc){
    def array = []
    while(!to_boolean(is_empty(proc))){
        array.push(first(proc))
        proc = rest(proc)
    }
    return array
}

def my_list = unshift(
       unshift(
         unshift(empty)(three)
       )(two)
     )(one)
println to_array(my_list).collect{to_integer(it)}

/*def results = (one..hundred).collect{number ->
    IF(isZero(mod(number)(fifteen)))("FizzBuzz")(number)
}*/

//assert results.findAll{it == "FizzBuzz"}.size() == 6
//println results.join(", ")


/*
println to_integer(pow(one)(one))
println to_integer(five)
println IF(FALSE, "foo", "bar")
println to_boolean(isZero(zero))
println to_boolean(isZero(hundred))
println to_integer(increment(one))
println to_integer(decrement(one))
println to_integer(first(my_list))
println to_integer(first(rest(my_list)))
println to_integer(first(rest(rest(my_list))))
println to_boolean(is_empty(my_list))
println to_boolean(is_empty(empty))
*/


