require '../src/Trait'
require '../src/Class'
require '../src/Symbol'
require '../src/conflict_handler'
require '../src/conflict_resolution'

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


  uses MiTrait  +
           (MiOtroTrait2 <= ConflictResolution.custom(
               function: proc  {"hola"}))
end

o = Conflicto.new
puts o.metodo1
puts o.metodo2(84)
