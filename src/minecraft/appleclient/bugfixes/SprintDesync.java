package appleclient.bugfixes;

import com.google.common.eventbus.Subscribe;

import appleclient.events.impl.EventAttack;
import appleclient.events.impl.EventPacketReceive;
import appleclient.interfaces.IMinecraft;
import net.minecraft.entity.DataWatcher;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;

public class SprintDesync implements IMinecraft
{
    public static SprintDesync FIX = new SprintDesync();
    private boolean attacked = false, stop = false;
    
    @Subscribe
    public void onAttack(EventAttack e)
    {
        attacked = true;
    }
    
    @Subscribe
    public void onPacketReceive(EventPacketReceive e)
    {
        if (e.packet instanceof S1CPacketEntityMetadata)
        {
            S1CPacketEntityMetadata packet = (S1CPacketEntityMetadata) e.packet;
            
            if (mc.player != null && packet.getEntityId() == mc.player.getEntityId())
            {
                for (DataWatcher.WatchableObject watchable : packet.func_149376_c())
                {
                    if (watchable.getDataValueId() == 0 && attacked)
                    {
                        boolean sprint = ((byte) watchable.getObject() & 1 << 3) != 0;
                        
                        if (!sprint && mc.player.isSprinting())
                        {
                            stop = !(attacked = false);
                        }
                    }
                }
            }
        }
    }
    
    public boolean needsStop()
    {
        return stop;
    }
    
    public void finish()
    {
        stop = false;
    }
}
