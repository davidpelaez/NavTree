class CreateEdges < ActiveRecord::Migration
  def self.up
    create_table :edges do |t|
      t.integer :user_id

      t.timestamps
    end
  end

  def self.down
    drop_table :edges
  end
end
