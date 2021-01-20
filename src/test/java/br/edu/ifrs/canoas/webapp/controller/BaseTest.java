package br.edu.ifrs.canoas.webapp.controller;


import br.edu.ifrs.canoas.webapp.MockAuthContext;
import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.enums.CommentStatus;
import br.edu.ifrs.canoas.webapp.service.AnnounceService;
import br.edu.ifrs.canoas.webapp.service.CommentService;
import br.edu.ifrs.canoas.webapp.service.UserDetailsImplService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.edu.ifrs.canoas.webapp.domain.User;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Import(MockAuthContext.class)
public abstract class BaseTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    MockAuthContext mockAuthContext;
    @MockBean
    UserDetailsImplService implService;
    @MockBean
    Messages messages;
    @MockBean
    AnnounceService announceService;
    @MockBean
    CommentService commentService;

    @BeforeEach
    public void setUp() {
        when(announceService.countAll(any(AnnounceStatus.class))).thenReturn(5L);
        when(announceService.countAll(any(User.class), any(AnnounceStatus.class))).thenReturn(5L);
        when(commentService.countAll(any(CommentStatus.class))).thenReturn(3L);
    }

    @AfterEach
    public void tearDown() {
        this.mockAuthContext.tearDown();
    }


}