class ConflictResolution

  attr_accessor  :conflict_type, :functions

  def self.exec_all()
    ConflictResolution.new(ConflictType::EXEC_ALL)
  end

  def self.exec_if(condition:, option:)
    ConflictResolution.new(ConflictType::EXEC_IF, [condition, option])
  end

  def self.exec_fold(function:)
    ConflictResolution.new(ConflictType::FOLD, [function])
  end

  def self.custom(function:)
    ConflictResolution.new(ConflictType::CUSTOM, [function])
  end

  def initialize(conflictType, functions_resolution = {})
    @conflict_type = conflictType
    @functions = functions_resolution
  end

end