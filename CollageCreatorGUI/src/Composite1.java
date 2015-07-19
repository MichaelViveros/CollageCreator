import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;


public class Composite1 extends Composite {
	private Text txtInputDirectory;
	private Text txtOutputName;
	private Label lblStatus;
	
	private String inDirectory, outName;
	private int numRows, numColumns;
	private String command; // command to run collage creator script
	
	private final String GIMP_PATH = "C:\\Program Files\\GIMP 2\\bin\\gimp-2.8.exe";
	private final String GIMP_FLAGS = " -i -b ";
	private final String COMMAND_NAME = "\"(create-collage ";
	private Spinner spinnerNumberOfCollages;
	private Spinner spinnerNumColumns;
	private Spinner spinnerNumRows;
	private Combo comboPictureFormat;

	/**
	 * Create the composite.
	 * @param this
	 * @param style
	 */
	public Composite1(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label lblInputDirectory = new Label(this, SWT.NONE);
		lblInputDirectory.setText("Input Directory");
		
		txtInputDirectory = new Text(this, SWT.BORDER);
		txtInputDirectory.setText("C:\\Users\\Owner\\Pictures\\CLOUD 2015\\Collage2");
		txtInputDirectory.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// open system dialog to select input directory
				// TODO: make OS call to open file explorer dialog
			}
		});
		txtInputDirectory.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblOutputName = new Label(this, SWT.NONE);
		lblOutputName.setText("Output Directory");
		
		txtOutputName = new Text(this, SWT.BORDER);
		txtOutputName.setText("C:\\Users\\Owner\\Pictures\\CLOUD 2015");
		txtOutputName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPictureFormat = new Label(this, SWT.NONE);
		lblPictureFormat.setText("Picture Format");
		
		comboPictureFormat = new Combo(this, SWT.NONE);
		comboPictureFormat.setItems(new String[] {"jpg\t", "png\t", "bmp"});
		comboPictureFormat.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboPictureFormat.select(0);
		
		Label lblNumberOfRows = new Label(this, SWT.NONE);
		lblNumberOfRows.setText("Number of Rows");
		
		spinnerNumRows = new Spinner(this, SWT.BORDER);
		spinnerNumRows.setMaximum(10);
		spinnerNumRows.setMinimum(1);
		
		Label lblNumberOfColumns = new Label(this, SWT.NONE);
		lblNumberOfColumns.setText("Number of Columns");
		
		spinnerNumColumns = new Spinner(this, SWT.BORDER);
		spinnerNumColumns.setMaximum(10);
		spinnerNumColumns.setMinimum(1);
		
		Label lblNumberOfCollages = new Label(this, SWT.NONE);
		lblNumberOfCollages.setText("Number of Collages");
		
		spinnerNumberOfCollages = new Spinner(this, SWT.BORDER);
		spinnerNumberOfCollages.setMaximum(10);
		spinnerNumberOfCollages.setMinimum(1);
		
		lblStatus = new Label(this, SWT.NONE);
		lblStatus.setText("Status");
		
		Button btnCreateCollage = new Button(this, SWT.NONE);
		btnCreateCollage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblStatus.setText("Creating collage ...");
				
				boolean result = GimpWrapper.ExecuteCommand(
					txtInputDirectory.getText(), 
					txtOutputName.getText(), 
					comboPictureFormat.getText().trim(), 
					Integer.parseInt(spinnerNumRows.getText()), 
					Integer.parseInt(spinnerNumColumns.getText()),
					Integer.parseInt(spinnerNumberOfCollages.getText())
				);

				if (result)
					lblStatus.setText("Success!");
				else
					lblStatus.setText("Error!");
				
			}
		});
		btnCreateCollage.setText("Create Collage");
		//new Label(this, SWT.NONE);
		
		/*Text helloWorldTest = new Text(this, SWT.NONE);
		helloWorldTest.setText("Hello World SWT");
		helloWorldTest.pack();*/
		this.pack();
		new Label(this, SWT.NONE);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
