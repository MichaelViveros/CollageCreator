import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {

    public static void main(String[] args) {
        Display display = new Display();

        Shell shell = new Shell(display);
        
        // the layout manager handle the layout
        // of the widgets in the container
        //shell.setLayout(new FillLayout());
        
        //TODO add some widgets to the Shell
        Composite1 comp = new Composite1(shell, SWT.NONE);
        
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }
} 