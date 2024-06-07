package appleclient.mods.events;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.google.common.eventbus.Subscribe;

public class EventBus
{
    private final ArrayList<Object> registeredObjects = new ArrayList();
    
    public void register(Object object)
    {
        registeredObjects.add(object);
    }
    
    public void unregister(Object object)
    {
        registeredObjects.remove(object);
    }
    
    public void post(Object object)
    {
        try
        {
            for (int i = 0; i < registeredObjects.size(); i++)
            {
                Object clazz = registeredObjects.get(i);
                Class originalClazz = clazz.getClass();
                
                for (Method method : originalClazz.getMethods())
                {
                    if (method.isAnnotationPresent(Subscribe.class))
                    {
                        for (Class parameter : method.getParameterTypes())
                        {
                            if (parameter.getName().equals(object.getClass().getName()))
                            {
                                try
                                {
                                    method.invoke(clazz, object);
                                } catch (Exception e) {}
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {}
    }
}
