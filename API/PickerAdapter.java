public class PickerAdapter extends ArrayAdapter<Picker> {

private ArrayList<Picker> data;
Context context;

private TextView tvName;

public PickerAdapter(ArrayList<Picker> data, Context context){
super(context, R.id.list_picker, data);
this.data = data;
this.context = context;
} // closing PickerAdapter

@Override
public View getView(int position, View convertView, ViewGroup parent) {
Picker picker = getItem(position);
final View result;

LayoutInflater inflater = LayoutInflater.from(getContext());
convertView = inflater.inflate(R.layout.list_picker, parent, false);

tvName = convertView.findViewById(R.id.item_picker);
tvName.setText(picker.getName());

return convertView;

} // closing class