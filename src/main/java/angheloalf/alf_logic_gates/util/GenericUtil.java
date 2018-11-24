package angheloalf.alf_logic_gates.util;

public final class GenericUtil{
    public static boolean arrayContains(Object[] array, Object value){
        for(Object i: array){
            if(i == value || i.equals(value)){
                return true;
            }
        }
        return false;
    }
}
