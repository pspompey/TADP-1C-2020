require 'rspec'
require_relative '../src/Trait'
require_relative '../src/Class'
require_relative '../src/Symbol'
require_relative '../src/conflict_resolution'

Trait.define do
  name :MiTrait
  method :metodo1 do
    "Hola"
  end
  method :metodo2 do |un_numero|
    un_numero * 0 + 42
  end
  method :metodo3 do
    "Scorpion"
  end
  method :metodo4 do
    "Goku"
  end
end

Trait.define do
  name :MiOtroTrait
  method :metodo2 do|un_numero|
    un_numero / 1 + 0
  end
  method :metodo3 do
    "SubZero"
  end
  method :metodo4 do
    "Vegeta"
  end
end


class Conflict
  uses MiTrait + (MiOtroTrait <= {:metodo2 => ConflictResolutionExecAll.new,
                                  :metodo3 => ConflictResolutionExecIf.new(condition: Proc.new { |resultado| resultado == "Liu Kang" },
                                                                           option: Proc.new { "Finish Him!!" }
                                  )})
end

conflicts = Conflict.new

describe "ConflictResolution" do
  before do

  end

  context "executes metodo1 without conflict" do
    it "returns Hola" do
      expect(conflicts.metodo1).to eql("Hola")
    end
  end

  context "executes methods with conflicts" do
    context "metodo2 given 33 has exec_all resolution" do
      it 'returns 42 and 33 ' do
        expect(conflicts.metodo2(33)).to eql([42,33])
      end
    end

    context "metodo3 has exec_if resolution " do
      it 'return Finish Him!!' do
        expect(conflicts.metodo3).to eql("Finish Him!!")
      end
    end

    context "metodo4 has not resolution specified" do
      it 'should execute default resolution which raise and exception' do
        expect{conflicts.metodo4}.to raise_error(DuplicateMethodError)
      end
    end

  end


end
