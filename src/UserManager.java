import java.util.ArrayList;

class UserManager {
    private ArrayList<User> users;

    public UserManager(){
        users = FileManager.loadUsers();
    }

    public void saveUsers(){
        users = FileManager.loadUsers();
    }

    public void addUser(User user){
        User found = findUser(user.getUsername());
        if(found.getUsername().equals(user.getUsername())){
            System.out.println("Error: User already exist");
            return;
        }
        users.add(user);
    }

    public User findUser(String username){
        for (User user : users){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public User login(String username,String password){
        User found = findUser(username);
        if (found != null && found.checkPassword(password)){
            return found;
        }
        return null;
    }

    public ArrayList<User> getUsers(){
        return users;
    }
}
