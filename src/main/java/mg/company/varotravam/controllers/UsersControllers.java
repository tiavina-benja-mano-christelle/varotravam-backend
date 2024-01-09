package mg.company.varotravam.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.company.varotravam.models.User;
import mg.company.varotravam.utils.Bag;
import mg.company.varotravam.utils.JWTtokens;

@RestController
@RequestMapping("/log")
public class UsersControllers {
    
    @PostMapping("/in")
    public ResponseEntity<Bag> check(@RequestBody User users) {
        Bag bag = new Bag();
        System.out.println(users);
        try {
            User user = User.check(users.getUsername(), users.getPassword());
            user.setTokens(JWTtokens.create(user.getId(), 3600000));
            bag.setData(user.getTokens());
            return new ResponseEntity<Bag>(bag, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            bag.setError(e.getMessage());
            return new ResponseEntity<Bag>(bag, HttpStatus.UNAUTHORIZED);
        }
    }
}
