import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/app/domains/shared/components/table";
import type { CategoryData } from "../schemas/Category";
import { getEmojiById } from "./EmojiFunctions";
import { useTranslation } from "react-i18next";
type CategoriesTableProps = {
  categories: CategoryData[];
};

export function CategoriesTable({ categories }: CategoriesTableProps) {
  const { t } = useTranslation();

  return (
    <div className="flex flex-col items-center justify-center px-4">
      <Table>
        <TableCaption className="text-white">
          {t('domains.category.modify.tableCaption')}
        </TableCaption>
        <TableHeader>
          <TableRow>
            <TableHead className="text-left text-white pl-8">
              {t('domains.category.name')}
            </TableHead>
            <TableHead className="text-right text-white">
              {t('domains.category.totalSpending')}
            </TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {categories.map((category) => (
            <TableRow key={category.id}>
              <TableCell className="font-medium text-white">
                <div className="flex items-center gap-2 pl-8">
                  {category.icon && (
                    <span className="flex-shrink-0">
                      {getEmojiById(category.icon)}
                    </span>
                  )}
                  <span>{category.name}</span>
                </div>
              </TableCell>
              <TableCell className="text-right">
                {category.totalSpending?.toFixed(2)}€
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}