package com.app.liferdeal.ui.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "Item_Descreption",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create TABLE item_table (item_id text,item_name text,size_item_id text,size_item_name text,extra_item_id text,extra_item_name text,price number,item_quantity number)");
      //  sqLiteDatabase.execSQL("Create TABLE deal_item_table (deal_item_id text,deal_item_name text,deal_price number,deal_item_quantity number)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS item_table");
      //  sqLiteDatabase.execSQL("DROP TABLE IF EXISTS deal_item_table");
    }



    public void update_item_all(String item_id, String size_item_id, String size_item_name, String extra_item_id, String extra_item_name, double price, int quant)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update item_table set size_item_id ='"+size_item_id+"',size_item_name='"+size_item_name+"',extra_item_id='"+extra_item_id+"',extra_item_name='"+extra_item_name+"',price="+price+",item_quantity="+quant+" where item_id = "+item_id);
        db.close();
    }

    public void InsertItem(String item_id, String item_name, String size_item_id, String size_item_name, String extra_item_id, String extra_item_name, double price, int quant)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String insert="insert into item_table(item_id,item_name,size_item_id,size_item_name,extra_item_id,extra_item_name,price,item_quantity) values('"+item_id+"','"+item_name+"','"+size_item_id+"','"+size_item_name+"','"+extra_item_id+"','"+extra_item_name+"','"+price+"','"+quant+"')";
        db.execSQL(insert);
        db.close();
    }
    public void delete()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String delete = "delete from item_table";
        db.execSQL(delete);
        db.close();
    }
    public void delete_Item(String item_idd)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String delete1 = "delete from item_table where item_id = "+item_idd;
        db.execSQL(delete1);
        db.close();
    }
    public void delete_Item_size(String item_idd, String item_size_id, String extra_item_size_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String delete1 = "delete from item_table where item_id = "+item_idd+" AND size_item_id = "+item_size_id+" AND extra_item_id = '"+extra_item_size_id+"'";
        db.execSQL(delete1);
        db.close();
    }
    public void delete_Item_size(String item_idd, String item_size_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String delete1 = "delete from item_table where item_id = "+item_idd+" AND size_item_id = "+item_size_id;
        db.execSQL(delete1);
        db.close();
    }
    public void delete_Item_extra(String item_idd, String item_extra_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String delete1 = "delete from item_table where item_id = "+item_idd+" AND extra_item_id = '"+item_extra_id+"'";
        db.execSQL(delete1);
        db.close();
    }
    public void delete_Item_both(String item_idd, String size_itemm_id, String item_extra_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String delete1 = "delete from item_table where item_id = "+item_idd+" AND extra_item_id = '"+item_extra_id+"' AND size_item_id ='"+size_itemm_id+"'";
        db.execSQL(delete1);
        db.close();
    }
    public void update_item(String id, int q, double p)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update item_table set item_quantity ="+q+",price="+p+" where item_id = "+id);
        db.close();
    }
    public void update_item_size(String id, String size_id, int q, double p)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update item_table set item_quantity ='"+q+"',price='"+p+"' where item_id = '"+id+"' AND size_item_id = '"+size_id+"'");
        db.close();
    }
    public void update_item_size_with_topping(String id, String size_id, String extra_item_id, int q, double p)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update item_table set item_quantity ='"+q+"',price='"+p+"' where item_id = '"+id+"' AND size_item_id = '"+size_id+"' AND extra_item_id = '"+extra_item_id+"'");
        db.close();
    }
    public void update_extra_quantity(String id, String extra_item_id, double price, int quant)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update item_table set price='"+price+"',item_quantity='"+quant+"' where  item_id = '"+id+"' AND extra_item_id = '"+extra_item_id+"'");
        db.close();
    }
    public void update_both_quantity(String id, String size_itemm_id, String extra_item_id, double price, int quant)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update item_table set price='"+price+"',item_quantity='"+quant+"' where  item_id = '"+id+"' AND extra_item_id = '"+extra_item_id+"' AND size_item_id ='"+size_itemm_id+"'" );
        db.close();
    }


    public void deal_InsertItem(String deal_item_id, String deal_item_name, double deal_price, int deal_quant)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String insert="insert into deal_item_table(deal_item_id,deal_item_name,deal_price,deal_item_quantity) values('"+deal_item_id+"','"+deal_item_name+"','"+deal_price+"','"+deal_quant+"')";
        db.execSQL(insert);
        db.close();
    }

    public void deal_update_item(String deal_id, int deal_q, double deal_p)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update deal_item_table set deal_item_quantity ="+deal_q+",deal_price="+deal_p+" where deal_item_id = "+deal_id);
        db.close();
    }

    public void deal_delete_Item(String deal_item_idd)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String delete1 = "delete from deal_item_table where deal_item_id = "+deal_item_idd;
        db.execSQL(delete1);
        db.close();
    }
    public void deal_delete()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String delete = "delete from deal_item_table";
        db.execSQL(delete);
        db.close();
    }








}