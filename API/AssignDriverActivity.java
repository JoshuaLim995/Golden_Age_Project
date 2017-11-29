public class AssignDriverActivity extends AppCompatActivity implements View.OnClickListener {

TextInputLayout til_driver, til_patient, til_nurse, til_location, til_description, til_date, til_time;
EditText etDriver, etPatient, etNurse, etLocation, etDescription, etDate, etTime;
Dialog dialog;
ListView listView;

private String driver_id, nurse_id, patient_id;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_assign_driver);
Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
setSupportActionBar(toolbar);

initialize();

} // closing onCreate

private void initialize(){

etDriver = (EditText) findViewById(R.id.item_driver);
etPatient = (EditText) findViewById(R.id.item_patient);
etNurse = (EditText) findViewById(R.id.item_nurse);
etLocation = (EditText) findViewById(R.id.item_location);
etDescription = (EditText) findViewById(R.id.item_description);
etDate = (EditText) findViewById(R.id.item_schedule_date);
etTime = (EditText) findViewById(R.id.item_schedule_time);

til_driver = (TextInputLayout) findViewById(R.id.til_driver);
til_patient = (TextInputLayout) findViewById(R.id.til_patient);
til_nurse = (TextInputLayout) findViewById(R.id.til_nurse);
til_location = (TextInputLayout) findViewById(R.id.til_location);
til_description = (TextInputLayout) findViewById(R.id.til_description);
til_date = (TextInputLayout) findViewById(R.id.til_schedule_date);
til_time = (TextInputLayout) findViewById(R.id.til_schedule_time);

//Initialize the Dialog
dialog = new Dialog(this);
dialog.setContentView(R.layout.picker_dialog);
listView = (ListView) dialog.findViewById(R.id.list_picker);

} // closing initialize


public void showDriverPickerDialog(View view){

String selection = DatabaseContract.UserContract.REG_TYPE + " = ?";
String[] selectionArgs = {"D"};

String[] columns = {
DatabaseContract.UserContract._ID,
DatabaseContract.UserContract.NAME
}; // closing columns

Cursor cursor = dbq.query(DatabaseContract.UserContract.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

ArrayList<Picker> pickerList = new ArrayList<>();

if(cursor.moveToFirst()){
            do{
                pickerList.add(new Picker(
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.UserContract._ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.UserContract.NAME))
                ));
            }while (cursor.moveToNext());
        }
showPicker("Select Driver", pickerList);

//OnClickListener for listView
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Picker picker = (Picker) adapterView.getAdapter().getItem(position);
                
                etDriver.setText(picker.getName());
                driver_id = picker.getID();
            }
        });

} // closing showDriverPickerDialog


private void showPicker(String title, ArrayList<Picker> pickerList){

//final Dialog dialog = new Dialog(this);
//dialog.setContentView(R.layout.picker_dialog);
//dialog.setTitle(title);
//ListView listView = (ListView) dialog.findViewById(R.id.list_picker);
PickerAdapter pickerAdapter = new PickerAdapter(pickerList, getActivity());
listView.setAdapter(adapter);
dialog.show();

} // closing showPicker


public void showDatePickerDialog(View view){

// Get Current Date
final Calendar c = Calendar.getInstance();
mYear = c.get(Calendar.YEAR);
mMonth = c.get(Calendar.MONTH);
mDay = c.get(Calendar.DAY_OF_MONTH);

DatePickerDialog datePickerDialog = new DatePickerDialog(this, 
	new DatePickerDialog.OnDateSetListener() {

@Override
public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    etDate.setText(year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);

datePickerDialog.show();

} //closing showDatePicker

public void showTimePickerDialog(View view){

// Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            etTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();

} //closing showTimePicker

} // closing class