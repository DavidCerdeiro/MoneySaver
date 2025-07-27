import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { useTranslation } from 'react-i18next';
import { CategoriesTable } from "../components/CategoriesTable";
import { ModifyCategoryForm } from "../components/ModifyCategoryForm";
import { useEffect, useState } from "react";
import { obtainAllCategories } from "../application/CategoryService";
import type { CategoryData } from "../schemas/Category";

export function ModifyCategoryPage() {
  const { t } = useTranslation();
  const [categories, setCategories] = useState<CategoryData[]>([]);

  const fetchCategories = async () => {
    try {
      const data = await obtainAllCategories({ idUser: 1 });
      setCategories(data.categories);
    } catch (error) {
      console.error("Error loading categories", error);
    }
  };

  useEffect(() => {
    fetchCategories();
  }, []);

  return (
    <DefaultPageLayout>
      <div className="modify-category-page">
        <h1 className="page-title">{t('header.sections.spendings.editCategory.title')}</h1>
        <p className="page-description">{t('domains.category.modify.description')}</p>
        <CategoriesTable categories={categories} />
        <div className="mt-5">
            <ModifyCategoryForm categories={categories} refreshCategories={fetchCategories} />
        </div>
      </div>
    </DefaultPageLayout>
  );
}