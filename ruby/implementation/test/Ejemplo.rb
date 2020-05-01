require '../src/Trait'
require '../src/Class'
require '../src/Symbol'
require '../src/conflict_handler'

Trait.define do
  name :MiTrait
  method :metodo1 do
    "Hola"
  end
  method :metodo2 do |un_numero|
    un_numero * 0 + 42
  end
end

Trait.define do
  name :MiOtroTrait
  method :metodo2 do|un_numero|
    un_numero / 1 + 0
  end
  method :metodo3 do
    "zaraza"
  end
end

Trait.define do
  name :MiOtroTrait2
  method :metodo2 do |otronumero|
     otronumero * 3
  end
end

class Conflicto
  uses MiTrait + (MiOtroTrait <= ConflictResolution.new(ConflictType::EXEC_IF,
                                                        [Proc.new {|un_numero| un_numero > 1000},
                                                         Proc.new  {|un_numero| un_numero + 3}])) +
           (MiOtroTrait2 <= ConflictResolution.new(ConflictType::FOLD, [Proc.new  {|un_numero, otro_numero| un_numero + otro_numero}]))
end

o = Conflicto.new
puts o.metodo1
puts o.metodo2(84)
puts o.metodo3