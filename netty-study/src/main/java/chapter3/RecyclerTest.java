package chapter3;

import io.netty.util.Recycler;

/**
 * @author yuzhang
 * @date 2020/12/7 下午8:07
 * TODO
 */
public class RecyclerTest {
    private static final Recycler<User> userRecycler = new Recycler<User>() {
        @Override
        protected User newObject(Handle<User> handle) {
            return new User(handle);
        }
    };

    static final class User{
        private String name;
        private Recycler.Handle<User> handle;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public User(Recycler.Handle<User> handle){
            this.handle = handle;
        }

        public void recycle(){
            handle.recycle(this);
        }
    }

    public static void main(String[] args) {
        // 从对象池获取User对象
        User user = userRecycler.get();
        // 设置User对象的属性
        user.setName("hello");
        // 回收对象到对象池
        user.recycle();
        // 从对象池获取对象
        User user1 = userRecycler.get();
        System.out.println(user1.getName());
        System.out.println(user == user1);
    }
}
