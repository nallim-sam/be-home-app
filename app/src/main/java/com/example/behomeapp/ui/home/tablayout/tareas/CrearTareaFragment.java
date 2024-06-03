package com.example.behomeapp.ui.home.tablayout.tareas;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.behomeapp.DBManager.DataBaseManager;
import com.example.behomeapp.DBManager.TareaManager;
import com.example.behomeapp.DBManager.UserManager;
import com.example.behomeapp.R;
import com.example.behomeapp.enums.FrecuenciaEnum;
import com.example.behomeapp.model.TareaModelo;
import com.example.behomeapp.util.SharedPreferencesUtils;

import java.util.Calendar;
import java.util.logging.Logger;


public class CrearTareaFragment extends Fragment {

    private static final Logger log = Logger.getLogger(CrearTareaFragment.class.getName());

    private EditText editTextNombre;
    private EditText editTextFecha;
    private Spinner spinnerFrecuencia;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_tarea, container, false);

        editTextNombre = view.findViewById(R.id.editTextNombreLista);
        editTextFecha = view.findViewById(R.id.editTextFecha);
        spinnerFrecuencia = view.findViewById(R.id.spinnerFrecuencia);
        final Button buttonCrearTarea = view.findViewById(R.id.buttonCrearTarea);
        final Button buttonVolver = view.findViewById(R.id.buttonVolver);

        // Configurar el Spinner de frecuencia
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.frecuencia_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrecuencia.setAdapter(adapter);

        // Configurar el DatePicker para la fecha
        editTextFecha.setOnClickListener(v -> showDatePickerDialog());

        // Configurar el botón de crear tarea
        buttonCrearTarea.setOnClickListener(v -> {
            new Thread(this::crearTarea).start();
        });

        buttonVolver.setOnClickListener(v -> getParentFragmentManager().popBackStack());

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

    private void crearTarea() {
        String nombre = editTextNombre.getText().toString().trim();
        String fecha = editTextFecha.getText().toString().trim();
        String frecuenciaString = spinnerFrecuencia.getSelectedItem().toString();

        if (nombre.isEmpty() || fecha.isEmpty() || frecuenciaString.isEmpty()) {
            // Mostrar mensaje de error si algún campo está vacío
            log.info("No se ha podido crear la tarea porque algun campo esta vacio.");
            return;
        }

        FrecuenciaEnum frecuencia;
        switch (frecuenciaString) {
            case "Diario":
                frecuencia = FrecuenciaEnum.DIARIO;
                break;
            case "Semanal":
                frecuencia = FrecuenciaEnum.SEMANAL;
                break;
            case "Mensual":
                frecuencia = FrecuenciaEnum.MENSUAL;
                break;
            case "Ninguna" :
                frecuencia = FrecuenciaEnum.NINGUNA;
            default:
                frecuencia = FrecuenciaEnum.NINGUNA; // valor por defecto
        }

        // Crear nueva tarea
        TareaModelo tarea = new TareaModelo();
        tarea.setNombre(nombre);
        tarea.setFechaLimite(fecha);
        tarea.setFrecuencia(frecuencia);
        tarea.setCompletado(false);

        final String email = SharedPreferencesUtils.getEmail(requireContext());
        tarea.setIdPiso(DataBaseManager.obtenerPisoId(email));
        tarea.setIdUsuario(UserManager.obtenerUsuarioId(email));

        // Insertar tarea en la base de datos
        TareaManager.insertarTarea(tarea);

        // Regresar al fragmento anterior o cerrar el fragmento actual
        getActivity().getSupportFragmentManager().popBackStack();
    }
}