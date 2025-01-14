# This file is auto-generated from the current state of the database. Instead of editing this file, 
# please use the migrations feature of Active Record to incrementally modify your database, and
# then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your database schema. If you need
# to create the application database on another system, you should be using db:schema:load, not running
# all the migrations from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20111124233929) do

  create_table "edges", :force => true do |t|
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "node_id"
    t.text     "ancestry"
    t.integer  "secret_id"
    t.integer  "extra"
    t.text     "title"
  end

  create_table "nodes", :force => true do |t|
    t.boolean   "filtered"
    t.timestamp "created_at"
    t.timestamp "updated_at"
    t.text      "url"
  end

  create_table "secrets", :force => true do |t|
    t.string    "private_key"
    t.integer   "user_id"
    t.timestamp "created_at"
    t.timestamp "updated_at"
    t.integer   "public_key"
  end

  create_table "users", :force => true do |t|
    t.string    "email"
    t.string    "email2"
    t.string    "encrypted_password", :limit => 128
    t.string    "salt",               :limit => 128
    t.string    "confirmation_token", :limit => 128
    t.string    "remember_token",     :limit => 128
    t.string    "name",               :limit => 128
    t.integer   "fbid"
    t.boolean   "email_confirmed",                   :default => false, :null => false
    t.timestamp "created_at"
    t.timestamp "updated_at"
  end

  add_index "users", ["email"], :name => "index_users_on_email"
  add_index "users", ["fbid"], :name => "index_users_on_fbid"
  add_index "users", ["id", "confirmation_token"], :name => "index_users_on_id_and_confirmation_token"
  add_index "users", ["remember_token"], :name => "index_users_on_remember_token"

end
