package asignaciones;

import java.util.Comparator;
import java.util.Calendar;

public class DateBasedComparator implements Comparator<Asignacion>{
    
    @Override
    public int compare(Asignacion A1, Asignacion A2){
        return A1.compareTo(A2);
    }
}

class ImportanceBasedComparator implements Comparator<Asignacion>{
    
    @Override
    public int compare(Asignacion A1, Asignacion A2){
        return A1.compareTo(A2);
    }
}