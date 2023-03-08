import java.sql.*;


public class SQLAccess {
    private static Connection connection =null;

    public int sc;

    public SQLAccess() {

        try {
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/garage", "root", "210100");
            System.out.println("connection successful\n");
            Statement s = c.createStatement();

        }
        catch (SQLException e)
        {
            System.out.println(e.toString());
        }

    }
    public void InsertDB(String name, int score){
        boolean isExist =false;
        String nameSnake = "Snake";
        try {
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/garage", "root", "210100");
            System.out.println("connection successful\n");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM " + nameSnake.toLowerCase());
            while (rs.next())
            {
                if(rs.getString("Name").equals(name)){
                   isExist=true;
                   sc = Integer.parseInt(rs.getString("Score"));
                   System.out.println("co");
                }

            }
            if(isExist){

                if(score>sc){
                    String sqlcmd = "DELETE FROM " + nameSnake.toLowerCase() +
                            " WHERE Name = "+ "'"+name+"'" ;
                    s.executeUpdate(sqlcmd);
                    sqlcmd = "INSERT INTO " + nameSnake.toLowerCase() +
                            " (Name, Score) VALUES ('"+name+"' ,"+ score+");";
                    s.executeUpdate(sqlcmd);
                }

            }
            else {
                String sqlcmd = "INSERT INTO " + nameSnake.toLowerCase() +
                        " (Name, Score) VALUES ('"+name+"' ,"+ score+");";
                s.executeUpdate(sqlcmd);
            }


        }
        catch (SQLException e)
        {
            System.out.println(e.toString());
        }
    }
    public void ReadDB(){
        String nameSnake = "Snake";
            try {
                Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/garage", "root", "210100");
                System.out.println("connection successful\n");
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM " + nameSnake.toLowerCase());
                while (rs.next())
                {
                    Player a =new Player();
                    a.name = rs.getString("Name");
                    a.Score = Integer.parseInt(rs.getString("Score"));
                    GameSnake.player.add(a);
                }
            }
            catch (SQLException e)
            {
                System.out.println(e.toString());
            }
        }


}
