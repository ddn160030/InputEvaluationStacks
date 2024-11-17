public class GenericStack<E> {
    private java.util.ArrayList<E> list = new java.util.ArrayList<>();

    public int getSize() {
        return list.size();
    }

    public E peek() {
        if (list.isEmpty())
            return null;
        else
            return list.get(getSize() - 1);
    }

    public void push(E o) {
        list.add(o);
    }

    public E pop() {
        if (list.isEmpty())
            return null;
        else
        {
            E o = list.get(getSize() - 1);
            list.remove(getSize() - 1);
            return o;
        }
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        if (list.isEmpty())
            return "Stack is empty";
        else
        {
            String output = list.toString();
            output = output.replace('[', ' ');
            output = output.replace(']',' ');
            return "GENERIC STACK: " + output;
        }
    }
}
