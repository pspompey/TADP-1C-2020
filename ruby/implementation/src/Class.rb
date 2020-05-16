class Class

  def uses(the_trait)
    the_trait.define_methods self
  end
end