package com.bankmanager.application.views;

import com.bankmanager.application.components.buttons.CustomButton;
import com.bankmanager.application.services.user.UserService;
import com.bankmanager.application.views.despesas.DespesaView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 title;
    private final UserService userService;

    public MainLayout(UserService userService) {
        this.userService = userService;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private Component getHeaderLayout() {
        var layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        
        title = new H2();
        title.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        var buttonLogout = new CustomButton(VaadinIcon.EXIT.create());
        buttonLogout.addClickListener(event -> getUI().ifPresent(userService::logout));

        layout.add(
                title,
                buttonLogout);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        return layout;
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");
        addToNavbar(true, toggle, getHeaderLayout());
    }

    private void addDrawerContent() {
        H1 appName = new H1("Bank Manager");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller);
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Despesas a pagar", DespesaView.class, LineAwesomeIcon.DOLLAR_SIGN_SOLID.create()));

        return nav;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        title.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
