require 'rspec'
require '../spec/Trait'
require '../spec/Class'
require '../spec/Symbol'
require '../spec/conflict_resolution'

Trait.define do
  name :MiTrait
  method :metodo1 do |un_numero|
    un_numero * 1 + 1
  end
end

Trait.define do
  name :MiOtroTrait
  method :metodo1 do|un_numero|
    un_numero / 1 + 1
  end
end

class ConflictoCustom
  uses MiTrait + (MiOtroTrait <= {:metodo1 => ConflictResolutionCustom.new(
      function: proc {
        "hola"
      }
  )})
end

custom = ConflictoCustom.new


describe 'ConflictResolution' do

    it 'al invocar metodo1 devuelvo "hola" definido en custom' do
      expect(custom.metodo1(10)).to eq "hola"
    end
end
