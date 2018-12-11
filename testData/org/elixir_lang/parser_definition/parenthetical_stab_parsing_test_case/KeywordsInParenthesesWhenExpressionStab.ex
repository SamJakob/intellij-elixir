( (key_one: value_one, key_two: value_two) when ()  -> )
( (key_one: value_one, key_two: value_two) when function positional, key: value  -> )
( (key_one: value_one, key_two: value_two) when &one  -> )
( (key_one: value_one, key_two: value_two) when one \\ default  -> )
( (key_one: value_one, key_two: value_two) when one when key: value  -> )
( (key_one: value_one, key_two: value_two) when one when guard  -> )
( (key_one: value_one, key_two: value_two) when one :: type  -> )
( (key_one: value_one, key_two: value_two) when one | two  -> )
( (key_one: value_one, key_two: value_two) when one = two  -> )
( (key_one: value_one, key_two: value_two) when one or two  -> )
( (key_one: value_one, key_two: value_two) when one || two  -> )
( (key_one: value_one, key_two: value_two) when one and two  -> )
( (key_one: value_one, key_two: value_two) when one && two  -> )
( (key_one: value_one, key_two: value_two) when one != two  -> )
( (key_one: value_one, key_two: value_two) when one < two  -> )
( (key_one: value_one, key_two: value_two) when one + two  -> )
( (key_one: value_one, key_two: value_two) when one / two  -> )
( (key_one: value_one, key_two: value_two) when one * two  -> )
( (key_one: value_one, key_two: value_two) when one ^^^ two  -> )
( (key_one: value_one, key_two: value_two) when ! one  -> )
( (key_one: value_one, key_two: value_two) when not one  -> )
( (key_one: value_one, key_two: value_two) when Module.function positional, key: value  -> )
( (key_one: value_one, key_two: value_two) when @function positional, key: value  -> )
( (key_one: value_one, key_two: value_two) when function positional, key: value  -> )
( (key_one: value_one, key_two: value_two) when One.Two[key]  -> )
( (key_one: value_one, key_two: value_two) when Module.function[key]  -> )
( (key_one: value_one, key_two: value_two) when Module.function()  -> )
( (key_one: value_one, key_two: value_two) when Module.function  -> )
( (key_one: value_one, key_two: value_two) when @variable[key]  -> )
( (key_one: value_one, key_two: value_two) when @variable  -> )
( (key_one: value_one, key_two: value_two) when function positional, key: value  -> )
( (key_one: value_one, key_two: value_two) when variable[key]  -> )
( (key_one: value_one, key_two: value_two) when variable  -> )
( (key_one: value_one, key_two: value_two) when @1  -> )
( (key_one: value_one, key_two: value_two) when &1  -> )
( (key_one: value_one, key_two: value_two) when !1  -> )
( (key_one: value_one, key_two: value_two) when not 1  -> )
( (key_one: value_one, key_two: value_two) when fn  -> end -> )
( (key_one: value_one, key_two: value_two) when 1  -> )
( (key_one: value_one, key_two: value_two) when []  -> )
( (key_one: value_one, key_two: value_two) when "one"  -> )
( (key_one: value_one, key_two: value_two) when """
                                                one
                                                """ -> )
( (key_one: value_one, key_two: value_two) when 'one'  -> )
( (key_one: value_one, key_two: value_two) when '''
                                                one
                                                ''' -> )
( (key_one: value_one, key_two: value_two) when ~x{sigil}modifiers  -> )
( (key_one: value_one, key_two: value_two) when true  -> )
( (key_one: value_one, key_two: value_two) when :atom  -> )
( (key_one: value_one, key_two: value_two) when Alias  -> )
