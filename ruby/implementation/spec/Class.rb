class Class

  def uses(*the_traits)
    the_traits.each do | the_trait |
      the_trait.methods.each do |selector, method|
        self.send(:define_method, selector, method)
      end
    end
  end
end