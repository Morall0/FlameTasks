package asignaciones;

import java.util.Comparator;
import java.util.Calendar;

public class DateBasedComparator implements Comparator<Asignacion>{
    
    @Override
    public int compare(Asignacion A1, Asignacion A2){
        return A1.fecha.compareTo(A2.fecha);
    }
}

class ImportanceBasedComparator implements Comparator<Asignacion>{
    
    @Override
    public int compare(Asignacion A1, Asignacion A2){
        return A1.importancia.compareTo(A2.importancia);
    }
}