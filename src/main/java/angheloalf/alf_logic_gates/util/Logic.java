package angheloalf.alf_logic_gates.util;

import angheloalf.alf_logic_gates.Config;
import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class Logic{
    public static int repeatSignalOrPower(int power){
        if(power <= 0){
            return 0;
        }
        if(Config.repeatSignal){
            return 15;
        }
        return power > 15 ? 15 : power;
    }

    public static int buffer(int a){
        return repeatSignalOrPower(a);
    }

    public static int negate(int power){
        return power == 0 ? 15: 0;
    }

    public static int and(int a, int b){
        int value = a < b ? a: b;
        return repeatSignalOrPower(value);
    }

    public static int or(int a, int b){
        int value = a > b ? a: b;
        return repeatSignalOrPower(value);
    }

    public static int xor(int a, int b){
        int value = 0;
        if(b == 0){
            value = a;
        }
        else if(a == 0){
            value = b;
        }
        return repeatSignalOrPower(value);
    }
}
