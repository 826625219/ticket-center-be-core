// 具体某个人开发的线路报障业务
@Component
class LineTicketOperation implements TicketOperation {
    // 由于@Component注解，Spring会自动装配该类，static中的方法会被调用。
    static {
        TicketFactory.register("Line", RemoteServiceLog.class);
    }

    public void create(Object data) {
        // Implementation
    }
}


// 工单工厂
class TicketFactory {
    private static final Map<String, Class<? extends TicketOperation>> instances = new HashMap<>();

    public static void register(String ticketType, Class<? extends TicketOperation> instance) {
        if (ticketType != null && instance != null) {
            instances.put(ticketType, instance);
        }
    }
    // 得到实例对象
    public static TicketOperation getInstance(ApplicationContext context, String ticketType) {
        if (instances.containsKey(ticketType)) {
            return context.getBean(instances.get(ticketType));
        }
        return null;
    }
}

// 规定好接口，具体涉及到工单的业务都要去实现便于工单中心统一调用，但是也可以有自己的拓展方法。
public interface TicketOperation {
     void create(Object data);
     
     void update(Object data);
     // Others
}



//调用方 

TicketFactory.getInstance(context,"Line").create()
