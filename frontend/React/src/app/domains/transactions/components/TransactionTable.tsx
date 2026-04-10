import {
  Table,
  TableBody,
  TableCell,
  TableCaption,
  TableHead,
  TableHeader,
  TableRow,
} from "@/app/domains/shared/components/table";
import { useTranslation } from "react-i18next";
import type { TransactionResponse } from "../schemas/TransactionResponse";
import { useMemo, useState } from "react";
import { getEmojiById } from "../../category/components/EmojiFunctions";
import { ArrowUp, ArrowDown } from "lucide-react";
type TransactionTableProps = {
  transactions: TransactionResponse[];
};

type SortableKeys = keyof TransactionResponse;
type SortDirection = "ascending" | "descending";
type SortConfig = {
  key: SortableKeys;
  direction: SortDirection;
};

export function TransactionsTable({ transactions }: TransactionTableProps) {
  const { t } = useTranslation();
  
  const [sortConfig, setSortConfig] = useState<SortConfig>({
    key: 'date',
    direction: 'descending',
  });

  const sortedTransactions = useMemo(() => {
    const sortableItems = [...transactions];

    sortableItems.sort((a, b) => {
      const aValue = a[sortConfig.key];
      const bValue = b[sortConfig.key];

      if (aValue == null) return 1; 
      if (bValue == null) return -1; 
      
      if (aValue < bValue) {
        return sortConfig.direction === 'ascending' ? -1 : 1;
      }
      if (aValue > bValue) {
        return sortConfig.direction === 'ascending' ? 1 : -1;
      }
      
      return 0;
    });

    return sortableItems;
  }, [transactions, sortConfig]);

  const requestSort = (key: SortableKeys) => {
    let direction: SortDirection = 'ascending';
    if (sortConfig.key === key && sortConfig.direction === 'ascending') {
      direction = 'descending';
    }
    setSortConfig({ key, direction });
  };

  const getSortIcon = (columnKey: SortableKeys) => {
    if (sortConfig.key !== columnKey) return null;
    return sortConfig.direction === 'ascending' 
      ? <ArrowUp className="inline-block ml-2 h-4 w-4" /> 
      : <ArrowDown className="inline-block ml-2 h-4 w-4" />;
  };

  return (
    <div className="table-container">
      <Table>
          <TableCaption className="table-caption">
              {t('domains.transaction.table.caption')}
          </TableCaption>
          <TableHeader>
              <TableRow>
                  <TableHead 
                    className="table-head cursor-pointer hover:bg-gray-700" 
                    onClick={() => requestSort('categoryName')}
                  >
                    <span className="span-text font-medium">
                      {t('domains.spending.category')}
                      {getSortIcon('categoryName')}
                    </span>
                  </TableHead>
                  <TableHead 
                    className="table-head cursor-pointer hover:bg-gray-700" 
                    onClick={() => requestSort('accountName')}
                  >
                    <span className="span-text font-medium">
                        {t('domains.account.title')}
                        {getSortIcon('accountName')}
                    </span>
                  </TableHead>
                  <TableHead 
                    className="table-head cursor-pointer hover:bg-gray-700" 
                    onClick={() => requestSort('accountNumber')}
                  >
                    <span className="span-text font-medium">
                        {t('domains.account.number')}
                        {getSortIcon('accountNumber')}
                    </span>
                  </TableHead>
                  <TableHead 
                    className="table-head cursor-pointer hover:bg-gray-700" 
                    onClick={() => requestSort('name')}
                  >
                    <span className="span-text font-medium">
                      {t('domains.spending.name')}
                      {getSortIcon('name')}
                    </span>
                  </TableHead>
                  <TableHead 
                    className="table-head cursor-pointer hover:bg-gray-700" 
                    onClick={() => requestSort('amount')}
                  >
                    <span className="span-text font-medium">
                      {t('domains.spending.amount')}
                      {getSortIcon('amount')}
                    </span>
                  </TableHead>
                  <TableHead 
                    className="table-head cursor-pointer hover:bg-gray-700" 
                    onClick={() => requestSort('date')}
                  >
                    <span className="span-text font-medium">
                      {t('domains.spending.date')}
                      {getSortIcon('date')}
                    </span>
                  </TableHead>
                  <TableHead 
                    className="table-head cursor-pointer hover:bg-gray-700" 
                    onClick={() => requestSort('establishmentName')}
                  >
                    <span className="span-text font-medium">
                      {t('domains.establishment.title')}
                      {getSortIcon('establishmentName')}
                    </span>
                  </TableHead>
              </TableRow>
          </TableHeader>
          <TableBody>
              {sortedTransactions.map((transaction) => (
                  <TableRow key={transaction.id}>
                      <TableCell className="table-cell">
                          {getEmojiById(transaction.categoryIcon)} {transaction.categoryName === "Deleted" ? t('domains.category.deleted') : transaction.categoryName}
                      </TableCell>
                      <TableCell className="table-cell">
                        <span className="span-text">
                          {transaction.accountName}
                        </span>
                      </TableCell>
                      <TableCell className="table-cell">
                        <span className="span-text">
                          {transaction.accountNumber}
                        </span>
                      </TableCell>
                      <TableCell className="table-cell">
                        <span className="span-text">
                          {transaction.name}
                        </span>
                      </TableCell>
                      <TableCell className="table-cell-money">
                        <span className="span-text font-semibold">
                          {transaction.amount.toFixed(2)} €
                        </span>
                      </TableCell>
                      <TableCell className="table-cell">
                        <span className="span-text">
                          {new Date(transaction.date).toLocaleDateString('es-ES', {
                            day: '2-digit',
                            month: '2-digit',
                            year: 'numeric'
                          })}
                        </span>
                      </TableCell>
                      <TableCell className="table-cell">
                        <span className="span-text">
                          {transaction.establishmentName || '-'}
                        </span>
                      </TableCell>
                  </TableRow>
              ))}
          </TableBody>
      </Table>
    </div>
  );
}