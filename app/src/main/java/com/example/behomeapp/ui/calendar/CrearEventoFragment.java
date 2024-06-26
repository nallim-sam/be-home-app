package com.example.behomeapp.ui.calendar;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.behomeapp.DBManager.CalendarioManager;
import com.example.behomeapp.DBManager.DataBaseManager;
import com.example.behomeapp.R;
import com.example.behomeapp.model.EventoModelo;
import com.example.behomeapp.util.SharedPreferencesUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CrearEventoFragment extends Fragment {

    private EditText editTextNombre;
    private EditText editTextFecha;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crear_evento, container, false);

        setHasOptionsMenu(true);

        editTextNombre = view.findViewById(R.id.editTextNombreLista);
        editTextFecha = view.findViewById(R.id.editTextFecha);

        final Button buttonCrearEvento = view.findViewById(R.id.buttonCrearEvento);
        final Button buttonVolver = view.findViewById(R.id.buttonVolver);

        // Configurar el DatePicker para la fecha
        editTextFecha.setOnClickListener(v -> showDatePickerDialog());

        buttonCrearEvento.setOnClickListener(v -> {
            // Ejecutar la creación del evento en un nuevo hilo
            new Thread(this::crearEvento).start();
        });

        buttonVolver.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        return view;
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year1, month1, dayOfMonth) -> {
                    String fecha = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    editTextFecha.setText(fecha);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void crearEvento() {

        String nombre = editTextNombre.getText().toString().trim();
        String fecha = editTextFecha.getText().toString().trim();

        // Convertir la fecha de 'dd/MM/yyyy' a 'yyyy-MM-dd'
        String fechaFormateada = convertirFecha(fecha);

        EventoModelo eventoModelo = new EventoModelo();
        eventoModelo.setNombre(nombre);
        eventoModelo.setFecha(fechaFormateada);

        final String email = SharedPreferencesUtils.getUserEmail(requireContext());
        final String pisoId = DataBaseManager.obtenerPisoId(email);
        final int calendarioId = CalendarioManager.obtenerIdCalendario(pisoId);
        eventoModelo.setIdCalendario(calendarioId);

        // OBTENER ID CALENDARIO
        CalendarioManager.insertarEvento(eventoModelo);

        // Regresar al fragmento anterior o cerrar el fragmento actual
        //getActivity().getSupportFragmentManager().popBackStack();
        requireActivity().runOnUiThread(() -> getActivity().getSupportFragmentManager().popBackStack());


    }

    private String convertirFecha(String fecha) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = inputFormat.parse(fecha);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }



}