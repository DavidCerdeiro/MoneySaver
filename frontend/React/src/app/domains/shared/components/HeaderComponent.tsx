"use client"

import {
  NavigationMenu,
  NavigationMenuList,
  NavigationMenuItem,
  NavigationMenuTrigger,
  NavigationMenuContent,
  NavigationMenuLink,
} from "@/app/domains/shared/components/navigation-menu.js"


import { useTranslation } from 'react-i18next'
import { Link } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'
import { MobileNav } from "./MobileNav"
import { logout } from "../../user/application/UserService"
// This component is used to render a list item in the navigation menu
function ListItem({
  title,
  description,
  to,
}: {
  title: string
  description: string
  to: string
}) {
  return (
    // This component renders a single list item with a link
    <li>
      <NavigationMenuLink asChild>
        <Link
          to={to}
          className="header-link"
        >
          <div className="text-sm font-medium leading-none">{title}</div>
          <p className="line-clamp-2 text-sm text-zinc-400">
            {description}
          </p>
        </Link>
      </NavigationMenuLink>
    </li>
  )
}

export function HeaderComponent() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  // This function handles the logout action
  const handleLogout = () => {
    logout(); 
    navigate('/login')
  }

  return (
    <header className="main-header">
      <div className="container mx-auto flex items-center justify-between px-4">
        {/* LINK TO HOME */}
        <Link to="/home">
          <h1 className="text-xl font-bold">{t("app.title")}</h1>
        </Link>

        {/* Navigation Menu Desktop */}
        <NavigationMenu className="hidden md:flex">
          <NavigationMenuList className="space-x-4">
            { /* Section: PROFILE */}
            <NavigationMenuItem>
            <NavigationMenuTrigger className="nav-link">{t("header.sections.profile.title")}</NavigationMenuTrigger>
            <NavigationMenuContent>
              <ul className="navMenu-content">
                  <ListItem
                    to="/user/profile/edit"
                    title={t("header.sections.profile.editProfile.title")}
                    description={t("header.sections.profile.editProfile.description")}
                  />
                  <ListItem
                    to="/user/accounts"
                    title={t("header.sections.profile.manageAccounts.title")}
                    description={t("header.sections.profile.manageAccounts.description")}
                  />
                </ul>
            </NavigationMenuContent>
            </NavigationMenuItem>
            
            {/* Section: SPENDINGS */}
            <NavigationMenuItem>
              <NavigationMenuTrigger className="nav-link">{t("header.sections.spendings.title")}</NavigationMenuTrigger>
              <NavigationMenuContent>
                <ul className="navMenu-content">
                  <ListItem
                    to="/spendings/view"
                    title={t("header.sections.spendings.viewSpendings.title")}
                    description={t("header.sections.spendings.viewSpendings.description")}
                  />
                  <ListItem
                    to="/spendings/add"
                    title={t("header.sections.spendings.addSpending.title")}
                    description={t("header.sections.spendings.addSpending.description")}
                  />
                  <ListItem
                    to="/categories/edit"
                    title={t("header.sections.spendings.editCategory.title")}
                    description={t("header.sections.spendings.editCategory.description")}
                  />
                  <ListItem
                    to="/categories/add"
                    title={t("header.sections.spendings.addCategory.title")}
                    description={t("header.sections.spendings.addCategory.description")}
                  />
                  <ListItem
                    to="/spendings/transactions"
                    title={t("header.sections.spendings.transactions.title")}
                    description={t("header.sections.spendings.transactions.description")}
                  />
                </ul>
              </NavigationMenuContent>
            </NavigationMenuItem>

            {/*Section: SAVING GOALS */}
            <NavigationMenuItem>
              <NavigationMenuTrigger className="nav-link">{t("header.sections.savingGoals.title")}</NavigationMenuTrigger>
              <NavigationMenuContent>
                <ul className="navMenu-content">
                  <ListItem
                    to="/savingGoals/add"
                    title={t("header.sections.savingGoals.createGoal.title")}
                    description={t("header.sections.savingGoals.createGoal.description")}
                  />
                  <ListItem
                    to="/savingGoals/view"
                    title={t("header.sections.savingGoals.viewGoals.title")}
                    description={t("header.sections.savingGoals.viewGoals.description")}
                  />
                  <ListItem
                    to="/savingGoals/edit"
                    title={t("header.sections.savingGoals.editGoals.title")}
                    description={t("header.sections.savingGoals.editGoals.description")}
                  />
                </ul>
              </NavigationMenuContent>
            </NavigationMenuItem>

            {/*Section: CHARTS */}
            <NavigationMenuItem>
              <NavigationMenuTrigger className="nav-link">{t("header.sections.charts.title")}</NavigationMenuTrigger>
              <NavigationMenuContent>
                <ul className="navMenu-content">
                  <ListItem
                    to="/charts/view"
                    title={t("header.sections.charts.viewCharts.title")}
                    description={t("header.sections.charts.viewCharts.description")}
                  />
                  <ListItem
                    to="/charts/compare"
                    title={t("header.sections.charts.compareCharts.title")}
                    description={t("header.sections.charts.compareCharts.description")}
                  />
                </ul>
              </NavigationMenuContent>
            </NavigationMenuItem>
          </NavigationMenuList>
        </NavigationMenu>

        {/* LOGOUT */}
        <button
          onClick={handleLogout}
          className="hidden md:inline-block button-logout"
        >
          {t("header.logout")}
        </button>

        {/* Mobile Navigation */}
        <div className="md:hidden">
          <MobileNav />
        </div>

      </div>
    </header>
  )
}
