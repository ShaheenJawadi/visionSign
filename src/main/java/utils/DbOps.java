package utils;

public class DbOps {
    public DbOps() {
    }

    public String select(String table , String where , String orderBy ){

        String query="SELECT * FROM " + table ;

        if(!where.isEmpty()){
            query+=" where "+where+" = ?" ;
        }
        if(!orderBy.isEmpty()){
            query+=" ORDER BY "+orderBy ;
        }

        return  query;

    }
    public String delete(String table , String selector  ){

        String query="DELETE FROM "+table+" WHERE "+selector+"=?" ;



        return  query;

    }



}
