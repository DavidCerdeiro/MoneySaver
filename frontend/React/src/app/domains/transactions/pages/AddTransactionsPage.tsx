import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { useTranslation } from "react-i18next";
import { useLocation, useNavigate } from "react-router-dom";
import { AddTransactionForm } from "../components/AddTransactionForm";
import type { ExtractTransactionResponse } from "../schemas/ExtractTransactionResponse";
import type { CategoryData } from "../../category/schemas/Category";
import { useEffect, useState } from "react";
import { getCategories } from "../../category/application/CategoryService";
import { obtainAllEstablishments } from "../../spending/application/SpendingService";
import type { EstablishmentData } from "../../spending/schemas/Establishment";

export function AddTransactionsPage() {
  const { t } = useTranslation();
  const location = useLocation();
  const navigate = useNavigate();
  const [categories, setCategories] = useState<CategoryData[]>([]);
  const [establishments, setEstablishments] = useState<EstablishmentData[]>([]);
  const transactions = (location.state as { transactions?: ExtractTransactionResponse["transactions"] })?.transactions ?? [];
  const [currentIndex, setCurrentIndex] = useState(0);

  const loadEstablishment = async () => {
      try {
        obtainAllEstablishments()
          .then((data) => {
              // Adding option to create new establishment
              const withNewOption = [
                  { id: 0, name: "+ Añadir nuevo", country: "", city: "" },
                  ...data
              ];
              setEstablishments(withNewOption);
          })
          .catch(console.error);
      } catch (error) {
        console.error("Error refreshing categories", error);
      }
    };
  useEffect(() => {
    getCategories().then((data) => setCategories(data.categories)).catch(console.error);
    loadEstablishment();
  }, []);
  const handleTransactionSubmitted = () => {
    if (currentIndex + 1 < transactions.length) {
      setCurrentIndex((prev) => prev + 1);
    } else {
      navigate("/spendings/transactions");
    }
  };
  return (
    <DefaultPageLayout>
      <div className="mobile-spacing">
        <h1 className="page-title">{t("domains.transaction.add.title")}</h1>
        <p className="page-description">{t("domains.transaction.add.description")}</p>
        <AddTransactionForm response={transactions[currentIndex]} categories={categories} establishments={establishments} onSubmitted={handleTransactionSubmitted} />
      </div>
    </DefaultPageLayout>
  );
}