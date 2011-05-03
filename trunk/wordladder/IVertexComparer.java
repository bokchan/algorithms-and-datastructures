package wordladder;
import java.util.Comparator;


public interface IVertexComparer<T> extends Comparator<T> {
	boolean isNeighbour(T v1, T v2);
}
