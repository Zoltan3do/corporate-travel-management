package manu_barone.ViaggiAziendali.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import manu_barone.ViaggiAziendali.entities.Dipendente;
import manu_barone.ViaggiAziendali.entities.Prenotazione;
import manu_barone.ViaggiAziendali.payloads.DipendenteDTO;
import manu_barone.ViaggiAziendali.repositories.DipendenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class DipendenteSer {

    @Autowired
    private DipendenteRepo dipendenteRepo;

    @Autowired
    private Cloudinary c;

    public Dipendente save(DipendenteDTO body) {
        Dipendente d = new Dipendente(body.username(), body.nome(), body.cognome(), body.email());
        return this.dipendenteRepo.save(d);
    }

    public Page<Dipendente> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.dipendenteRepo.findAll(pageable);
    }

    public Dipendente findById(String dipendenteUser) {
        return this.dipendenteRepo.findById(dipendenteUser).orElseThrow(() -> new RuntimeException());
    }

    public void findByIdAndDelete(String id) {
        Dipendente p = this.findById(id);
        this.dipendenteRepo.delete(p);
    }

    public String aggiungiAvatar(String idDipendente, MultipartFile file) {
        String url = null;
        try {
            url = (String) c.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
            Dipendente fund = dipendenteRepo.findById(idDipendente).orElseThrow(()-> new RuntimeException("Dipendente non trovato"));
            fund.setAvatar(url);
            dipendenteRepo.save(fund);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return url;
    }


}
