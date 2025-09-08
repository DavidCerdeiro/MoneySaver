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
    <div className="form-container">
      <Table>
        <TableCaption className="table-caption">
          {t('domains.category.modify.tableCaption')}
        </TableCaption>
        <TableHeader>
          <TableRow>
            <TableHead className="table-head">
              <span className="span-text font-medium">
                {t('domains.category.name')}
              </span>
            </TableHead>
            <TableHead className="table-head">
              <span className="span-text font-medium">
                {t('domains.category.totalSpending')}
              </span>
            </TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {categories.map((category) => (
            <TableRow key={category.id}>
              <TableCell className="table-cell">
                <span className="span-text flex items-center gap-1">
                  {category.icon && (
                    <span className="flex-shrink-0">
                      {getEmojiById(category.icon)}
                    </span>
                  )}
                  <span>{category.name}</span>
                </span>
              </TableCell>
              <TableCell className="table-cell-money">
                <span className="span-text">
                  {category.totalSpending?.toFixed(2)} €
                </span>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}